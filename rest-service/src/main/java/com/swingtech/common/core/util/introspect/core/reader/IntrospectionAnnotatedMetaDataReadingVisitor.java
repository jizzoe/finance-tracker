package com.swingtech.common.core.util.introspect.core.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Attribute;
import org.springframework.asm.FieldVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.Type;
import org.springframework.asm.TypePath;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;

import com.swingtech.common.core.util.ErrorUtil;
import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.AnnotationDescription;
import com.swingtech.common.core.util.introspect.model.ClassDescription;
import com.swingtech.common.core.util.introspect.model.MethodDescription;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;

public class IntrospectionAnnotatedMetaDataReadingVisitor extends AnnotationMetadataReadingVisitor {

	private final Class classToIntrospect;
	private final Object objectToIntrospect;
	private final ClassDescription rootClassDescription;
	private final RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration;
	private final RootDescriptionBuildConfiguration rootDescriptionConfigurtion;
	private final List<Exception> exceptionList = new ArrayList<Exception>();
	
	public IntrospectionAnnotatedMetaDataReadingVisitor(ClassLoader classLoader, Class classToIntrospect,
			Object objectToIntrospect, ClassDescription rootClassDescription,
			RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration,
			RootDescriptionBuildConfiguration rootDescriptionConfigurtion) {
		super(classLoader);
		this.classToIntrospect = classToIntrospect;
		this.objectToIntrospect = objectToIntrospect;
		this.rootClassDescription = rootClassDescription;
		this.runtimeConfiguration = runtimeConfiguration;
		this.rootDescriptionConfigurtion = rootDescriptionConfigurtion;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodDescription methodDescription;
		try {
			methodDescription = rootClassDescription.newChildMethod();
		} catch (IntrospectionException e) {
			throw new RuntimeException("Error creating description.  Could not parse metadata. ", e);
		}
		
		methodDescription.visitMethod(access, name, desc, signature, exceptions);

		// Skip bridge methods - we're only interested in original annotation-defining user methods.
		// On JDK 8, we'd otherwise run into double detection of the same annotated method...
		if ((access & Opcodes.ACC_BRIDGE) != 0) {
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
		return new IntrospectionMethodVisitor(name, access, getClassName(),
				Type.getReturnType(desc).getClassName(), this.classLoader, this.methodMetadataSet, methodDescription);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		AnnotationDescription annotationDescription = null;
		
		try {
			annotationDescription = rootClassDescription.newChildAnnotation();
		} catch (IntrospectionException e) {
			throw new RuntimeException("Error creating description.  Could not parse metadata. Error:  " + ErrorUtil.getErrorMessageFromException(e), e);
		}
		
		return super.visitAnnotation(desc, visible);
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		System.out.println("visit outerClass...desc:  " + desc);
		// TODO Auto-generated method stub
		super.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		System.out.println("visit inner class...desc:  ");
		// TODO Auto-generated method stub
		super.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public void visitSource(String source, String debug) {
		System.out.println("visit source...desc:  ");
		// TODO Auto-generated method stub
		super.visitSource(source, debug);
	}

	@Override
	public void visitAttribute(Attribute attr) {
		System.out.println("visit attribute...desc:  ");
		// TODO Auto-generated method stub
		super.visitAttribute(attr);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		System.out.println("visit field...desc:  " + desc);
		// TODO Auto-generated method stub
		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		
		for (Entry<String, List<AnnotationAttributes>> entry : attributesMap.entrySet()) {
			
		}
		
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		System.out.println("visit type annotation...desc:  " + desc);
		// TODO Auto-generated method stub
		return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
	}

	public void printValues(Object...objects) {
		System.out.println();
	}

	@Override
	public String toString() {
		return "IntrospectionAnnotatedMetaDataReadingVisitor [classLoader=" + classLoader + ", annotationSet="
				+ annotationSet + ", metaAnnotationMap=" + metaAnnotationMap + ", attributesMap=" + attributesMap
				+ ", methodMetadataSet=" + methodMetadataSet + ", api=" + api + ", cv=" + cv + ", getAnnotationTypes()="
				+ getAnnotationTypes() + ", getClassName()=" + getClassName() + ", isInterface()=" + isInterface()
				+ ", isAnnotation()=" + isAnnotation() + ", isAbstract()=" + isAbstract() + ", isConcrete()="
				+ isConcrete() + ", isFinal()=" + isFinal() + ", isIndependent()=" + isIndependent()
				+ ", hasEnclosingClass()=" + hasEnclosingClass() + ", getEnclosingClassName()="
				+ getEnclosingClassName() + ", hasSuperClass()=" + hasSuperClass() + ", getSuperClassName()="
				+ getSuperClassName() + ", getInterfaceNames()=" + Arrays.toString(getInterfaceNames())
				+ ", getMemberClassNames()=" + Arrays.toString(getMemberClassNames()) + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public Class getClassToIntrospect() {
		return classToIntrospect;
	}

	public Object getObjectToIntrospect() {
		return objectToIntrospect;
	}

	public ClassDescription getRootClassDescription() {
		return rootClassDescription;
	}

	public RuntimeClassDescriptionBuilderConfiguration getRuntimeConfiguration() {
		return runtimeConfiguration;
	}

	public RootDescriptionBuildConfiguration getRootDescriptionConfigurtion() {
		return rootDescriptionConfigurtion;
	}

	public List<Exception> getExceptionList() {
		return exceptionList;
	}
}
