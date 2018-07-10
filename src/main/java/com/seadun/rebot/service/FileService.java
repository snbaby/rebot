package com.seadun.rebot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.ContractMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractDetailMapper contractDetailMapper;
	
	
	@Transactional
	public void fileImport(MultipartFile file) {
		List<String> eqNoList = new ArrayList<>();
		HSSFWorkbook hwb = null;
		try {
			hwb = new HSSFWorkbook(file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RebotException(RebotExceptionConstants.EXCEL_PRASE_ERROR_CODE,
					RebotExceptionConstants.EXCEL_PRASE_ERROR_MESSAGE,
					RebotExceptionConstants.EXCEL_PRASE_ERROR_HTTP_STATUS);
		}

		HSSFSheet hssfSheet = hwb.getSheetAt(0);
		int rowNo = hssfSheet.getLastRowNum();
		if (rowNo < 5) {
			log.error(">>>>>excel 行数为{},小于6行",rowNo+1);
			throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
					RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或不包含资产号",
					RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
		}

		HSSFRow hssfRow2 = hssfSheet.getRow(2);
		if(hssfRow2 == null) {
			log.error(">>>>>excel 第3行为空");
			throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
					RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或第3行为空",
					RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
		}
		HSSFCell hssfCellContractNo = null;
		String contractNo = null;
		try {
			hssfCellContractNo = hssfRow2.getCell(2);
			if(hssfCellContractNo!=null) {
				if(hssfCellContractNo.getCellTypeEnum() == CellType.NUMERIC) {
					contractNo = String.valueOf(hssfCellContractNo.getNumericCellValue());
				}else if(hssfCellContractNo.getCellTypeEnum() == CellType.STRING) {
					contractNo = hssfCellContractNo.getStringCellValue();
				}
			}
			if (StringUtils.isBlank(contractNo)) {
				log.error(">>>>>excel 合同编号为空");
				throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或合同编号为空",
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
					RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或合同编号为空",
					RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
		}
		
		//新增合同，如果合同存在则更新更新时间
		String uuid = UUID.randomUUID().toString();
		
		Contract contract = new Contract();
		contract.setId(uuid);
		contract.setContract(contractNo);
		contract.setStatus("NO");
		contract.setUptTime(new Date());
		contract.setCrtTime(new Date());
		
		contractMapper.insertSelective(contract);

		for (int i = 5; i <= rowNo; i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			if (hssfRow == null) {
				log.warn(">>>>>excel 合同编号：{}，中的第{}行為空",contractNo,i+1);
				continue;
			}

			HSSFCell hssfCellSqNo = hssfRow.getCell(1);
			HSSFCell hssfCellEqType = hssfRow.getCell(2);
			HSSFCell hssfCellEqNo = hssfRow.getCell(3);

			String sqNo = null;
			if (hssfCellSqNo != null) {
				if (hssfCellSqNo.getCellTypeEnum() == CellType.NUMERIC) {
					sqNo = String.valueOf(hssfCellSqNo.getNumericCellValue());
				} else if (hssfCellSqNo.getCellTypeEnum() == CellType.STRING) {
					sqNo = hssfCellSqNo.getStringCellValue();					
				}
			}

			String eqType = null;
			if (hssfCellEqType != null) {
				if (hssfCellEqType.getCellTypeEnum() == CellType.NUMERIC) {
					eqType = String.valueOf(hssfCellEqType.getNumericCellValue());
				} else if (hssfCellEqType.getCellTypeEnum() == CellType.STRING) {
					eqType = hssfCellEqType.getStringCellValue();					
				}
			}

			String eqNo = null;
			if (hssfCellEqNo != null) {
				if (hssfCellEqNo.getCellTypeEnum() == CellType.NUMERIC) {
					eqNo = String.valueOf(hssfCellEqNo.getNumericCellValue());
				} else if (hssfCellEqNo.getCellTypeEnum() == CellType.STRING) {
					eqNo = hssfCellEqNo.getStringCellValue();					
				}
				
				if(eqNoList.contains(eqNo)) {
					log.error(">>>>>excel 合同编号：{}，中的资产号：{}，重复",contractNo,eqNo);
					throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
							RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或第"+i+1+"行资产号重复",
							RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
				}else {
					if(StringUtils.isNotBlank(eqNo)) {
						eqNoList.add(eqNo);
					}
				}
			}
			
			log.debug(">>>>>excel 合同编号：{}，中的第{}行,sqNo为{}，eqType为{}，eqNo为{}",sqNo,eqType,eqNo);
			if (StringUtils.isBlank(sqNo) && StringUtils.isBlank(eqType) && StringUtils.isBlank(eqNo)) {
				log.warn(">>>>>excel 合同编号：{}，中的第{}行為空",contractNo,i+1);
				continue;
			} else if (StringUtils.isNotBlank(eqType) && StringUtils.isNotBlank(eqNo)) {
				//to-do 存入数据库
				//新增资产表，入存在，则更新状态
				ContractDetail contractDetail = new ContractDetail();
				contractDetail.setId(UUID.randomUUID().toString());
				contractDetail.setContractId(uuid);
				contractDetail.setEqNo(eqNo);
				contractDetail.setEqType(eqType);
				contractDetail.setCrtTime(new Date());
				contractDetail.setUptTime(new Date());
				contractDetailMapper.insertSelective(contractDetail);
			} else {
				log.error(">>>>>excel 合同编号：{}，中的第{}行，数据异常",contractNo,i+1);
				throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或第"+i+1+"行数据格式异常",
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
			}
		}
	}
	
	public static void main(String[] args) {
		List<String> eqNoList = new ArrayList<>();
		eqNoList.add(null);
		System.out.println(eqNoList.contains(null));
	}
}
