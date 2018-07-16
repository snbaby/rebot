package com.seadun.rebot.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seadun.rebot.constant.RebotConstants;
import com.seadun.rebot.constant.RebotExceptionConstants;
import com.seadun.rebot.entity.Contract;
import com.seadun.rebot.entity.ContractComputer;
import com.seadun.rebot.entity.ContractDetail;
import com.seadun.rebot.entity.Disk;
import com.seadun.rebot.entity.Memory;
import com.seadun.rebot.entity.Network;
import com.seadun.rebot.entity.RebotException;
import com.seadun.rebot.entity.Video;
import com.seadun.rebot.mapper.ContractComputerMapper;
import com.seadun.rebot.mapper.ContractDetailMapper;
import com.seadun.rebot.mapper.ContractMapper;
import com.seadun.rebot.mapper.DiskMapper;
import com.seadun.rebot.mapper.MemoryMapper;
import com.seadun.rebot.mapper.NetworkMapper;
import com.seadun.rebot.mapper.VideoMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractDetailMapper contractDetailMapper;
	@Autowired
	private ContractComputerMapper contractComputerMapper;
	@Autowired
	private DiskMapper diskMapper;
	@Autowired
	private NetworkMapper networkMapper;
	@Autowired
	private MemoryMapper memoryMapper;
	@Autowired
	private VideoMapper videoMapper;

	@Transactional
	public void fileImport(MultipartFile file) throws IOException {
		List<String> eqNoList = new ArrayList<>();
		HSSFWorkbook hwb = null;
		try {
			try {
				hwb = new HSSFWorkbook(file.getInputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RebotException(RebotExceptionConstants.EXCEL_PRASE_ERROR_CODE,
						RebotExceptionConstants.EXCEL_PRASE_ERROR_MESSAGE,
						RebotExceptionConstants.EXCEL_PRASE_ERROR_HTTP_STATUS);
			}

			HSSFSheet hssfSheet = hwb.getSheetAt(0);
			int rowNo = hssfSheet.getLastRowNum();
			if (rowNo < 5) {
				log.error(">>>>>excel 行数为{},小于6行", rowNo + 1);
				throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或不包含资产号",
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
			}

			HSSFRow hssfRow2 = hssfSheet.getRow(2);
			if (hssfRow2 == null) {
				log.error(">>>>>excel 第3行为空");
				throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或第3行为空",
						RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
			}
			HSSFCell hssfCellContractNo = null;
			String contractNo = null;
			try {
				hssfCellContractNo = hssfRow2.getCell(2);
				if (hssfCellContractNo != null) {
					if (hssfCellContractNo.getCellTypeEnum() == CellType.NUMERIC) {
						contractNo = String.valueOf(hssfCellContractNo.getNumericCellValue());
					} else if (hssfCellContractNo.getCellTypeEnum() == CellType.STRING) {
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

			// 新增合同，如果合同存在则更新更新时间
			String uuid = UUID.randomUUID().toString();

			Contract contract = new Contract();
			contract.setId(uuid);
			contract.setContract(contractNo);
			contract.setStatus(RebotConstants.CONTRACT_UNVERIFIED);
			contract.setUptTime(new Date());
			contract.setCrtTime(new Date());

			contractMapper.insertSelective(contract);

			for (int i = 5; i <= rowNo; i++) {
				HSSFRow hssfRow = hssfSheet.getRow(i);
				if (hssfRow == null) {
					log.warn(">>>>>excel 合同编号：{}，中的第{}行為空", contractNo, i + 1);
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

					if (eqNoList.contains(eqNo)) {
						log.error(">>>>>excel 合同编号：{}，中的资产号：{}，重复", contractNo, eqNo);
						throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
								RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或第" + i + 1 + "行资产号重复",
								RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
					} else {
						if (StringUtils.isNotBlank(eqNo)) {
							eqNoList.add(eqNo);
						}
					}
				}

				log.debug(">>>>>excel 合同编号：{}，中的第{}行,sqNo为{}，eqType为{}，eqNo为{}", sqNo, eqType, eqNo);
				if (StringUtils.isBlank(sqNo) && StringUtils.isBlank(eqType) && StringUtils.isBlank(eqNo)) {
					log.warn(">>>>>excel 合同编号：{}，中的第{}行為空", contractNo, i + 1);
					continue;
				} else if (StringUtils.isNotBlank(eqType) && StringUtils.isNotBlank(eqNo)) {
					// to-do 存入数据库
					// 新增资产表，入存在，则更新状态
					ContractDetail contractDetail = new ContractDetail();
					contractDetail.setId(UUID.randomUUID().toString());
					contractDetail.setContractId(uuid);
					contractDetail.setEqNo(eqNo);
					contractDetail.setEqType(eqType);
					contractDetail.setStatus(RebotConstants.CONTRACT_UNCONFIRM);
					contractDetail.setComputerId("");
					contractDetail.setCrtTime(new Date());
					contractDetail.setUptTime(new Date());
					contractDetailMapper.insertSelective(contractDetail);
				} else {
					log.error(">>>>>excel 合同编号：{}，中的第{}行，数据异常", contractNo, i + 1);
					throw new RebotException(RebotExceptionConstants.EXCEL_CONTENT_ERROR_CODE,
							RebotExceptionConstants.EXCEL_CONTENT_ERROR_MESSAGE + "或第" + i + 1 + "行数据格式异常",
							RebotExceptionConstants.EXCEL_CONTENT_ERROR_HTTP_STATUS);
				}
			}
		} finally {
			// TODO: handle finally clause
			if(hwb!=null) {
				hwb.close();
			}
		}
	}
	
	public void fileExport(HttpServletResponse response,String startTime,String endTime,String contract){
		InputStream inputStream = null;
		HSSFWorkbook hwb = null;
		OutputStream out = null;
		
		try {
			
			inputStream = getClass().getClassLoader().getResourceAsStream("export.xls");
			try {
				hwb = new HSSFWorkbook(inputStream);
				out = response.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RebotException(RebotExceptionConstants.EXCEL_PRASE_ERROR_CODE,
						RebotExceptionConstants.EXCEL_PRASE_ERROR_MESSAGE,
						RebotExceptionConstants.EXCEL_PRASE_ERROR_HTTP_STATUS);
			}
			
			//创建日期格式
			CreationHelper createDateHelper = hwb.getCreationHelper();
			CellStyle cellDateStyle = hwb.createCellStyle();
			cellDateStyle.setDataFormat(createDateHelper.createDataFormat().getFormat("yyyy/mm/dd hh:mm:ss"));
			cellDateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			
			//创建边框
			CellStyle cellBoderStyle = hwb.createCellStyle();
			cellBoderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellBoderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellBoderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellBoderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			
			HSSFSheet hssfSheet = hwb.getSheetAt(0);
			hssfSheet.setColumnWidth(16, 20 * 256);//设置第24行的宽度
			
			HSSFRow hssfRow2 = hssfSheet.getRow(2);
			HSSFCell hssfCell2d = hssfRow2.getCell(3);
			hssfCell2d.setCellValue(contract);//设置合同编号
			
			HSSFCell hssfCell2r = hssfRow2.getCell(16);
			hssfCell2r.setCellStyle(cellDateStyle);//设置格式为日期
			hssfCell2r.setCellValue(new Date());//设置合同编号
			
			
			List<ContractComputer> contractComputerList = contractComputerMapper.select(contract, "YES", startTime, endTime);
			int startRow = 5;
			contractComputerList.forEach(contractComputer ->{
				HSSFRow hssfRow = hssfSheet.createRow(startRow);
				HSSFCell cellNo = hssfRow.createCell(1);//序号
				cellNo.setCellValue(startRow-4);
				cellNo.setCellStyle(cellBoderStyle);
				
				HSSFCell cellEqType = hssfRow.createCell(2);//设备类型
				cellEqType.setCellValue(contractComputer.getEqType());
				cellEqType.setCellStyle(cellBoderStyle);
				
				HSSFCell cellEqNo = hssfRow.createCell(3);//资产号
				cellEqNo.setCellValue(contractComputer.getEqNo());
				cellEqNo.setCellStyle(cellBoderStyle);
				
				HSSFCell cellComputerName = hssfRow.createCell(4);//计算机名称
				cellComputerName.setCellValue(contractComputer.getEqNo());
				cellComputerName.setCellStyle(cellBoderStyle);
				
				HSSFCell cellOpSystem = hssfRow.createCell(5);//操作系统版本
				cellOpSystem.setCellValue(contractComputer.getOpSystem());
				cellOpSystem.setCellStyle(cellBoderStyle);
				
				HSSFCell cellOpInstallDate = hssfRow.createCell(6);//操作系统安装日期
				cellOpInstallDate.setCellValue(contractComputer.getOpInstallDate());
				cellOpInstallDate.setCellStyle(cellBoderStyle);
				
				HSSFCell cellDiskSn = hssfRow.createCell(7);//硬盘序列号
				HSSFCell cellDiskCapacity = hssfRow.createCell(8);//硬盘容量
				HSSFCell cellDiskInterfaceType = hssfRow.createCell(9);//硬盘接口类型
				HSSFCell cellDiskShellSn = hssfRow.createCell(10);//硬盘外壳号
				
				//获取磁盘
				Disk diskParam = new Disk();
				diskParam.setComputerId(contractComputer.getComputerId());
				List<Disk> diskList = diskMapper.select(diskParam);
				
				StringBuffer diskSn = new StringBuffer();
				StringBuffer diskCapacity = new StringBuffer();
				StringBuffer diskInterfaceType = new StringBuffer();
				StringBuffer diskShellSn = new StringBuffer();
				diskList.forEach(disk->{
					
					if(StringUtils.isNotBlank(disk.getDiskSn())) {
						diskSn.append(RebotConstants.SPLIT+disk.getDiskSn());
					}else {
						diskSn.append(RebotConstants.SPLIT);
					}
					
					if(StringUtils.isNotBlank(disk.getDiskCapacity())) {
						diskCapacity.append(RebotConstants.SPLIT+disk.getDiskCapacity());
					}else {
						diskCapacity.append(RebotConstants.SPLIT);
					}
					
					if(StringUtils.isNotBlank(disk.getDiskInterfaceType())) {
						diskInterfaceType.append(RebotConstants.SPLIT+disk.getDiskCapacity());
					}else {
						diskInterfaceType.append(RebotConstants.SPLIT);
					}
					
					if(StringUtils.isNotBlank(disk.getDiskShellSn())) {
						diskShellSn.append(RebotConstants.SPLIT+disk.getDiskShellSn());
					}else {
						diskShellSn.append(RebotConstants.SPLIT);
					}
					
				});
				
				cellDiskSn.setCellValue(diskSn.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellDiskSn.setCellStyle(cellBoderStyle);
				
				cellDiskCapacity.setCellValue(diskCapacity.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellDiskCapacity.setCellStyle(cellBoderStyle);
				
				cellDiskInterfaceType.setCellValue(diskInterfaceType.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellDiskInterfaceType.setCellStyle(cellBoderStyle);
				
				cellDiskShellSn.setCellValue(diskShellSn.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellDiskShellSn.setCellStyle(cellBoderStyle);
				
				HSSFCell cellMacAddress = hssfRow.createCell(11);//MAC地址
				
				Network networkParam = new Network();
				networkParam.setComputerId(contractComputer.getComputerId());
				List<Network> netwrokList =  networkMapper.select(networkParam);
				
				StringBuffer macAddress = new StringBuffer();
				
				netwrokList.forEach(network->{
					if(StringUtils.isNotBlank(network.getMacAddress())) {
						macAddress.append(RebotConstants.SPLIT+network.getMacAddress());
					}else {
						macAddress.append(RebotConstants.SPLIT);
					}
				});
				
				cellMacAddress.setCellValue(macAddress.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellMacAddress.setCellStyle(cellBoderStyle);
				
				Memory memoryParam = new Memory();
				memoryParam.setComputerId(contractComputer.getComputerId());
				List<Memory> memoryList=memoryMapper.select(memoryParam);
				
				StringBuffer memType = new StringBuffer();
				StringBuffer memCapacity = new StringBuffer();
				memoryList.forEach(memory->{
					if(StringUtils.isNotBlank(memory.getMemType())) {
						memType.append(RebotConstants.SPLIT+memory.getMemType());
					}else {
						memType.append(RebotConstants.SPLIT);
					}
					
					if(StringUtils.isNotBlank(memory.getMemCapacity())) {
						memCapacity.append(RebotConstants.SPLIT+memory.getMemCapacity());
					}else {
						memCapacity.append(RebotConstants.SPLIT);
					}
				});
				
				HSSFCell cellMemType = hssfRow.createCell(12);//内存类型
				cellMemType.setCellValue(memType.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellMemType.setCellStyle(cellBoderStyle);
				
				HSSFCell cellMemCapacity = hssfRow.createCell(13);//内存容量
				cellMemCapacity.setCellValue(memCapacity.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellMemCapacity.setCellStyle(cellBoderStyle);
				
				HSSFCell cellCpu = hssfRow.createCell(14);//CPU型号
				cellCpu.setCellValue(contractComputer.getCpu());
				cellCpu.setCellStyle(cellBoderStyle);
				
				HSSFCell cellVideoType = hssfRow.createCell(15);//显卡型号
				
				Video videoParam = new Video();
				videoParam.setComputerId(contractComputer.getComputerId());
				
				List<Video> videoList = videoMapper.select(videoParam);
				StringBuffer videoType = new StringBuffer();
				
				videoList.forEach(video->{
					if(StringUtils.isNotBlank(video.getVideoType())) {
						videoType.append(RebotConstants.SPLIT+video.getVideoType());
					}else {
						videoType.append(RebotConstants.SPLIT);
					}
				});
				
				cellVideoType.setCellValue(videoType.toString().replaceFirst(RebotConstants.SPLIT, ""));
				cellVideoType.setCellStyle(cellBoderStyle);
				
				HSSFCell cellBiosSn = hssfRow.createCell(16);//设备序列号
				cellBiosSn.setCellValue(contractComputer.getBiosSn());
				cellBiosSn.setCellStyle(cellBoderStyle);
				
				HSSFCell cellDisplayType = hssfRow.createCell(17);//显示器类别
				cellDisplayType.setCellStyle(cellBoderStyle);
				
				HSSFCell cellDisplaySize = hssfRow.createCell(18);//显示器尺寸
				cellDisplaySize.setCellStyle(cellBoderStyle);
			});
			try {
				hwb.write(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RebotException(RebotExceptionConstants.EXCEL_PRASE_ERROR_CODE,
						RebotExceptionConstants.EXCEL_PRASE_ERROR_MESSAGE,
						RebotExceptionConstants.EXCEL_PRASE_ERROR_HTTP_STATUS);
			}
		} finally {
			// TODO: handle finally clause
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(hwb!=null) {
				try {
					hwb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}

	public static void main(String[] args) {
		List<String> eqNoList = new ArrayList<>();
		eqNoList.add(null);
		System.out.println(eqNoList.contains(null));
	}
}
