package com.example.qa.suites;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import com.example.qa.tests.FirstTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({
    FirstTest.class,  
})
public class Suite {

}