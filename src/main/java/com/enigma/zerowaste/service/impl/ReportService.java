package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.dto.OrderItemSearchDTO;
import com.enigma.zerowaste.entity.OrderItem;
import com.enigma.zerowaste.repository.OrderItemRepository;
import com.enigma.zerowaste.specification.OrderItemSpecification;
import com.enigma.zerowaste.specification.OrderSpesification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private OrderItemRepository repository;

    public String exportReport(OrderItemSearchDTO orderItemSearchDTO) {
        try {
            Specification<OrderItem> orderItemSpecification = OrderItemSpecification.getSpecification(orderItemSearchDTO);
            List<OrderItem> orderList = repository.findAll(orderItemSpecification);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            //load file and compile it
            String path = "src\\main\\resources\\";
            File file = ResourceUtils.getFile("classpath:paymentHistoryReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Author", "MOCKNYUS TEAM");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\Payment History Report.pdf");

            return "report generated in path : " + path;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to export report. The error " + e;
        }

    }
}
