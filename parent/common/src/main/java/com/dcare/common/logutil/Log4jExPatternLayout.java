package com.dcare.common.logutil;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * busParent
 *
 * @author JamesTsai
 * @since 8/3/2016.
 */
public class Log4jExPatternLayout extends PatternLayout {
    public Log4jExPatternLayout(String pattern) {
        super(pattern);
    }

    public Log4jExPatternLayout() {
        super();
    }

    /**
     * 重写createPatternParser方法，返回PatternParser的子类
     */
    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new Log4jExPatternParser(pattern);
    }
}
