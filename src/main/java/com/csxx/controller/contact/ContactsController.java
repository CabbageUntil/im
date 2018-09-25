package com.csxx.controller.contact;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.dto.contact.Owner;
import com.csxx.service.contact.ContactService;
import com.csxx.utils.ResponseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/im/contacts")
public class ContactsController {

    @Autowired
    ContactService contactService;

    @PostMapping(value = "/api/upload")
    public ResponseEntity upload(@RequestBody Owner owner) throws UnsupportedEncodingException {
        contactService.upload(owner);
        return ResponseEntityUtil.success();
    }

    @PostMapping(value = "/api/download")
    public ResponseEntity download(@RequestBody Owner owner) {
        return ResponseEntityUtil.success(contactService.download(owner.getUserName()));
    }

    @GetMapping(value = "/api/getSyncTime")
    public ResponseEntity getSyncTime(@RequestParam(value = "userName") String userName) {
        return ResponseEntityUtil.success(contactService.getSyncTime(userName));
    }

    @PostMapping(value = "/api/get_user")
    public ResponseEntity getName(@RequestParam(value = "secret") String secret,
                                  @RequestParam(value = "owner") String owner) {
        return ResponseEntityUtil.success(contactService.getUserWithPhoneList(owner));
    }

    @PostMapping(value = "/api/get_phone")
    public ResponseEntity getPhone(@RequestParam(value = "secret") String secret,
                                   @RequestParam(value = "owner") String owner,
                                   @RequestParam(value = "name") String name) {
        return ResponseEntityUtil.success(contactService.getPhoneByName(owner, name));
    }

    @PostMapping(value = "/api/add_mobile")
    public ResponseEntity addMobile(@RequestParam(value = "secret") String secret,
                                    @RequestParam(value = "owner") String owner,
                                    @RequestParam(value = "name") String name,
                                    @RequestParam(value = "mobile") String mobile) {
        contactService.addMobile(owner, name, mobile);
        return ResponseEntityUtil.success();
    }

}
