package org.wsd.ixbrl.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/")
public class IXBRLController {

    @PostMapping("/processDocument")
    public ResponseEntity<?> processDocument(@RequestParam("file") MultipartFile multipart) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + multipart.getOriginalFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(multipart.getInputStream());

    }

}
