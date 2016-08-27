package com.swingtech.common.core.util.introspect.core.reader;

import java.util.Set;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Attribute;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.MethodMetadataReadingVisitor;

import com.swingtech.common.core.util.introspect.model.MethodDescription;

public class IntrospectionMethodVisitor extends MethodMetadataReadingVisitor {
	private final MethodMetadataReadingVisitor wrappedMethodMetaDataReadingVisitor;
	private final MethodDescription methodDescription;

	public IntrospectionMethodVisitor(
			String methodName, 
			int access, 
			String declaringClassName, 
			String returnTypeName,
			ClassLoader classLoader, 
			Set<MethodMetadata> methodMetadataSet,
			MethodDescription methodDescription) 
	{
		super(methodName, access, declaringClassName, returnTypeName, classLoader, methodMetadataSet);
		this.wrappedMethodMetaDataReadingVisitor = this;
		this.methodDescription = methodDescription;
		this.setMethodDescriptionFields();
	}

	public IntrospectionMethodVisitor(
			MethodMetadataReadingVisitor wrappedMethodMetaDataReadingVisitor, 
			int access,
			ClassLoader classLoader, 
			Set<MethodMetadata> methodMetadataSet,
			MethodDescription methodDescription) 
	{
		super(
				wrappedMethodMetaDataReadingVisitor.getMethodName(), 
				access, 
				wrappedMethodMetaDataReadingVisitor.getDeclaringClassName(), 
				wrappedMethodMetaDataReadingVisitor.getReturnTypeName(), 
				classLoader, 
				methodMetadataSet);
		
		this.wrappedMethodMetaDataReadingVisitor = wrappedMethodMetaDataReadingVisitor;
		this.methodDescription = methodDescription;
		this.setMethodDescriptionFields();
	}

	public void setMethodDescriptionFields() {
		methodDescription.setAccessModifiers(access);
		methodDescription.setDeclaringClassName(this.declaringClassName);
		methodDescription.setMethodName(methodName);
		methodDescription.setReturnTypeName(returnTypeName);
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return super.visitAnnotation(desc, visible);
	}

	@Override
	public void visitParameter(String name, int access) {
		// TODO Auto-generated method stub
		super.visitParameter(name, access);
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		// TODO Auto-generated method stub
		return super.visitAnnotationDefault();
	}

	@Override
	public void visitAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		super.visitAttribute(attr);
	}

	@Override
	public void visitEnd() {
		// TODO Auto-generated method stub
		super.visitEnd();
	}

}
