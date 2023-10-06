package com.suomea.image.ctrl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/svg")
public class SVGController {

    @PostMapping("/toPng")
    public void toPng(@RequestParam MultipartFile file,
                      @RequestParam(required = false, defaultValue = "100") Float width,
                      @RequestParam(required = false, defaultValue = "100") Float height,
                      HttpServletResponse response) throws Exception {
        String originalFilename = file.getOriginalFilename();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + originalFilename.substring(0, originalFilename.lastIndexOf(".")) + ".png");


        PNGTranscoder pngTranscoder = new PNGTranscoder();
        pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
        pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
        pngTranscoder.transcode(new TranscoderInput(file.getInputStream()), new TranscoderOutput(response.getOutputStream()));
    }
}
