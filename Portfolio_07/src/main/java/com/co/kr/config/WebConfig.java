package com.co.kr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class WebConfig implements WebMvcConfigurer{
	
	// 파일 리소스
	@Value("${uploadFile.resourcePath}")
	private String resourcePath;
	
	//파일 업로드
	@Value("${uploadFile.Path}")
	private String uploadpath;
//	파일 사이즈
	private final int FILE_MAX_SIZE = 10 * 1024 * 1024;
	
	//	인코딩
	private final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * 파일 리소스 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler(uploadpath)	//	/upload/** 파일을 불러올수 있다.
				.addResourceLocations(resourcePath);
	}

    /**
     * 파일 업로드 설정
     */

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding(DEFAULT_ENCODING); // 파일 인코딩 설정
	    multipartResolver.setMaxUploadSize(FILE_MAX_SIZE); // 10MB
	    multipartResolver.setMaxUploadSizePerFile(FILE_MAX_SIZE); // 10MB
		return multipartResolver;
	}
	
    /**
     * 메세지 소스를 생성한다.
     * @return
     */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		// 메세지 프로퍼티파일의 위치와 이름을 지정한다.
		source.setBasename("classpath:/messages/message");
		// 기본 인코딩을 지정한다.
		source.setDefaultEncoding("UTF-8");
		// 프로퍼티 파일의 변경을 감지할 시간 간격을 지정한다.
		source.setCacheSeconds(60);
		// 없는 메세지일 경우 예외를 발생시키는 대신 코드를 기본 메세지로 한다.
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	

    /**
     * 언어 변경을 위한 인터셉터를 생성한다.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }


	//  Interceptor를 이용해서 처리하므로 전역의 Cross Origin 처리를 해준다.
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//			.allowedOrigins("*")
//			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//			.maxAge(6000);
//	}



}
