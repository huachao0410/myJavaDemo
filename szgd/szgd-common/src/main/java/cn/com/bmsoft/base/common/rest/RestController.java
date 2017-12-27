package cn.com.bmsoft.base.common.rest;

import cn.com.bmsoft.base.common.ui.DataTableModel;
import cn.com.bmsoft.base.common.ui.DataTableRowModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * REST服务列表服务
 *
 */
@Controller("restController")
@RequestMapping("/doc")
public class RestController {

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/rest/index");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/list")
    public DataTableModel list(HttpServletRequest request) throws ClassNotFoundException {
        String url = request.getRequestURL().toString();
        int index = url.indexOf("/", 10);
        String baseURL = url.substring(0, index);
        String contextPath = request.getContextPath();
        DataTableModel dataGridModel = new DataTableModel();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        //TODO:base-package
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("cn.com.bmsoft");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class cls = Class.forName(beanDefinition.getBeanClassName());
            if (cls.equals(this.getClass())) {
                continue;
            }
            if (cls.isAnnotationPresent(RequestMapping.class)
                    && cls.isAnnotationPresent(Controller.class)) {
                RequestMapping clsMapping = (RequestMapping) cls.getAnnotation(RequestMapping.class);
                RestService restService = (RestService) cls.getAnnotation(RestService.class);
                Method[] methods = cls.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)
                            && method.isAnnotationPresent(ResponseBody.class)) {
                        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                        String[] values = methodMapping.value();
                        for (String value : values) {
                            if (clsMapping.value().length > 0) {
                                if ((clsMapping.value()[0] + value).startsWith("/rest/")) {
                                    RestMethod restMethod = method.getAnnotation(RestMethod.class);
                                    
                                    //service
                                    DataTableRowModel dataGridRowModel = new DataTableRowModel();
                                    String serviceName = cls.getSimpleName();
                                    if(restService != null) {
                                    	serviceName += "：" + restService.name();
                                    }
                                    dataGridRowModel.addAttribute("service", serviceName);
                                    //method
                                    String methodName = method.getName();
                                    if(restMethod != null) {
                                    	methodName += "：" + restMethod.name();
                                    }
                                    dataGridRowModel.addAttribute("method", methodName);
                                    //type
                                    StringBuilder type = new StringBuilder();
                                    RequestMethod[] requestMethods = methodMapping.method();
                                    for (RequestMethod requestMethod : requestMethods) {
                                        type.append(requestMethod.name()).append(" ");
                                    }
                                    if (requestMethods.length == 0) {
                                        type.append(RequestMethod.GET.name());
                                    }
                                    dataGridRowModel.addAttribute("type", type.toString());
                                    //url
                                    dataGridRowModel.addAttribute("url", baseURL + contextPath + clsMapping.value()[0] + value);
                                    //param
                                    List<String> params = new ArrayList<String>();
                                    Class[] clses = method.getParameterTypes();
                                    Annotation[][] annotationses = method.getParameterAnnotations();
                                    for (int i = 0; i < clses.length; i++) {
                                    	String paramName = "[" + clses[i].getName() + "]";
                                    	
                                        if (annotationses[i].length > 0) {
                                            if(annotationses[i].length > 1) {
                                                if(annotationses[i][1].annotationType().equals(RequestParam.class)) {
                                                    paramName += "  " + ((RequestParam) annotationses[i][1]).value();
                                                }
                                            }

                                        	if(annotationses[i][0].annotationType().equals(RestParam.class)) {
                                        		paramName += " ： " + ((RestParam) annotationses[i][0]).name();
                                        	}
                                        }
                                        params.add(paramName);
                                       
                                    }
                                    dataGridRowModel.addAttribute("params", params);
                                    dataGridModel.addRow(dataGridRowModel);
                                }
                            }
                        }
                    }
                }
            }
        }
        dataGridModel.sort("service", "method", "type");
        return dataGridModel;
    }
}
