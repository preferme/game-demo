package com.naver.music.admin.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final WebProperties webProperties;
	private final ApplicationContext applicationContext;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(
				"*"
			).allowedMethods(
				HttpMethod.GET.name(),
				HttpMethod.HEAD.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()
			);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations(webProperties.getResources().getStaticLocations())
			.resourceChain(false)
			.addResolver(new IndexViewResolver(applicationContext.getResource("classpath:static/index.html")));
	}

	@Slf4j
	private static class IndexViewResolver extends PathResourceResolver {
		private final Resource index;

		public IndexViewResolver(Resource index) {
			this.index = index;
		}

		@Override
		public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations,
			ResourceResolverChain chain) {
			Resource resource = super.resolveResource(request, requestPath, locations, chain);
			return resource == null ? index : resource;
		}

		@Override
		public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
			String urlPath = super.resolveUrlPath(resourcePath, locations, chain);
			try {
				return StringUtils.isEmpty(urlPath) ? index.getURL().toString() : resourcePath;
			} catch (IOException e) {
				return index.getFilename();
			}
		}
	}
}
