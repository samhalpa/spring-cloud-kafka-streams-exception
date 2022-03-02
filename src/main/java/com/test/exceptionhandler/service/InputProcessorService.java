package com.test.exceptionhandler.service;

import com.test.exceptionhandler.model.InputDto;
import com.test.exceptionhandler.model.OutputDto;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InputProcessorService {

    public Optional<OutputDto> process(InputDto inputDto) {
       log.info("Input: {}", inputDto);
       throw new RuntimeException("Exception is thrown to test the exception handler");
    }
}
