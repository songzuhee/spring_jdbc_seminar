package com.umc.src.Point;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
}
