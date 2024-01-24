package org.wsd.ixbrl.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wsd.ixbrl.service.IXBRLService;

import java.io.IOException;


@Tag(name = "File Controller", description = "Controller for home operations")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class FileController {

    private final IXBRLService ixbrlService;

    @PostMapping(value = "/processDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<?> processDocument(@RequestParam("file") MultipartFile multipart) throws IOException {

        String html = new String(multipart.getBytes());

        String converted = ixbrlService.convert(html);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + multipart.getOriginalFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(converted.getBytes());

    }

}
