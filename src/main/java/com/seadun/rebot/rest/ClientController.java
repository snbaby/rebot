package com.seadun.rebot.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.seadun.rebot.mapper.BaseResponseEntity;

@RestController
@RequestMapping("client")
public class ClientController {
	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "base-board", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postBaseBoard(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bios", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postBios(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "computer-system", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postComputerSystem(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "desktop", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postDesktop(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "desktop-monitor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postDesktopMonitor(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "disk-drive", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postDiskDrive(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "group-users", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postGroupUsers(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "his-usb", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postHisUSB(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "keyboard", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postKeyboard(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login-session", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postLoginSession(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "network-adapter", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postNetworkAdapter(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "network-adapter-configuration", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postNetworkAdapterConfiguration(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "nt-event-log-file", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postNtEventlogFile(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "operating-system", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postOperatingSystem(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pointing-device", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postPointingDevice(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "printer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postPrinter(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "process", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postProcess(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "processor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postProcessor(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "quick-fix-engineering", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postQuickFixEngineering(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rising", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postRising(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "service", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postService(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "share", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postShare(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "startup-command", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postStartupCommand(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "system-dev-log", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postSystemDevLog(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "usb-controller", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postUsbController(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "user-account", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postUserAccount(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "user-in-domain", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postUserInDomain(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "video-controller", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResponseEntity> postVideoController(@RequestBody Object obj) throws Exception {
		return new ResponseEntity<BaseResponseEntity>(new BaseResponseEntity(), HttpStatus.CREATED);
	}
}
