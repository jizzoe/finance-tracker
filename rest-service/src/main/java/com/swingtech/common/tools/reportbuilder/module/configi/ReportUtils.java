	package com.swingtech.common.tools.reportbuilder.module.configi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;

import com.swingtech.common.tools.reportbuilder.modules.ui.ReportRootModule;

public class ReportUtils {

    public void foo() { }

    public int bar() { return 12; }

    public String baz() { return ""; }

    public static void main(String args[])
    {
    	
        try {
            Class c = ReportRootModule.class;
            Method[] m = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++) {
            	Method method = m[i];
            	System.out.println(m[i].toString());
            	Annotation[] annotations = AnnotationUtils.getAnnotations(method);
            	
            	for (Annotation annotation : annotations) {
                	System.out.println("   " + annotation);
            	}
            }
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
