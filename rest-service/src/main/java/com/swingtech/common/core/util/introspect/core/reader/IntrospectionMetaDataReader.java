package com.swingtech.common.core.util.introspect.core.reader;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.asm.ClassReader;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import com.swingtech.common.core.util.introspect.config.RuntimeClassDescriptionBuilderConfiguration;
import com.swingtech.common.core.util.introspect.model.ClassDescription;
import com.swingtech.common.core.util.introspect.model.config.RootDescriptionBuildConfiguration;

public class IntrospectionMetaDataReader implements MetadataReader {
	private final ClassMetadata classMetadata;

	private final AnnotationMetadata annotationMetadata;

	private final Class classToIntrospect;
	private final Object objectToIntrospect;
	private final ClassDescription rootClassDescription;
	private final RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration;
	private final RootDescriptionBuildConfiguration rootDescriptionConfigurtion;

	IntrospectionMetaDataReader(Object objectToIntrospect, ClassLoader classLoader, RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, ClassDescription classDescription) throws IOException {
		this(objectToIntrospect.getClass(), classLoader, runtimeConfiguration, classDescription);
	}
	
	IntrospectionMetaDataReader(Class<?> classToIntrospect, ClassLoader classLoader, RuntimeClassDescriptionBuilderConfiguration runtimeConfiguration, ClassDescription classDescription) throws IOException {
		
		InputStream is = classToIntrospect.getResourceAsStream(ClassUtils.getClassFileName(classToIntrospect));
		ClassReader classReader;
		try {
			classReader = new ClassReader(is);
		}
		catch (IllegalArgumentException ex) {
			throw new NestedIOException("ASM ClassReader failed to parse class file - " +
					"probably due to a new Java class file version that isn't supported yet: " + classToIntrospect, ex);
		}
		finally {
			is.close();
		}

		this.objectToIntrospect = null;
		// (since AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor)
		this.classToIntrospect = classToIntrospect;
		this.rootClassDescription = classDescription;
		this.runtimeConfiguration = runtimeConfiguration;
		this.rootDescriptionConfigurtion = this.rootClassDescription.getRootStaticConfiguration();
		

		IntrospectionAnnotatedMetaDataReadingVisitor visitor = new IntrospectionAnnotatedMetaDataReadingVisitor(classLoader, classToIntrospect, objectToIntrospect, classDescription, runtimeConfiguration,
				rootDescriptionConfigurtion);
		classReader.accept(visitor, ClassReader.SKIP_DEBUG);

		this.annotationMetadata = visitor;		this.classMetadata = visitor;

	}
	

	@Override
	public ClassMetadata getClassMetadata() {
		return this.classMetadata;
	}

	@Override
	public AnnotationMetadata getAnnotationMetadata() {
		return this.annotationMetadata;
	}


	@Override
	public Resource getResource() {
		// TODO Auto-generated method stub
		return null;
	}

}
