package com.umc.src.zzim;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZzimProvider {

    final Logger  logger = LoggerFactory.getLogger(this.getClass());

    private final ZzimDao zzimDao;



}
