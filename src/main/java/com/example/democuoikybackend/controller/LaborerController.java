package com.example.democuoikybackend.controller;

import com.example.democuoikybackend.adapter.ConverterType;
import com.example.democuoikybackend.adapter.DataConverter;
import com.example.democuoikybackend.adapter.ConverterFactory;
import com.example.democuoikybackend.command.*;
import com.example.democuoikybackend.model.Laborer;
import com.example.democuoikybackend.service.LaborerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/api/laborers/*")
public class LaborerController extends HttpServlet {
    private LaborerService laborerService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.laborerService = new LaborerService();
    }

    // Lấy danh sách (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        DataConverter dataConverter = resolveConverter(req);

        GetAllLaboreCommand getCmd = new GetAllLaboreCommand();
        laborerService.executeCommand(getCmd);

        String responseData = dataConverter.stringify(getCmd.getResultList());
        res.setContentType(resolveContentType(req));
        res.getWriter().write(responseData);
    }

    // Thêm/Cập nhật (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        DataConverter dataConverter = resolveConverter(req);

        String requestData = req.getReader().lines().collect(Collectors.joining());
        Laborer laborer = dataConverter.parse(requestData);

        Command addCmd = new AddLaborerCommand(laborer);
        laborerService.executeCommand(addCmd);

        res.setStatus(HttpServletResponse.SC_CREATED);
        res.setContentType(resolveContentType(req));
        res.getWriter().write("{\"message\": \"Lưu thành công!\"}");
    }

    // Cập nhật (PUT) - /api/laborers?id=1
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        DataConverter dataConverter = resolveConverter(req);

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setContentType(resolveContentType(req));
            res.getWriter().write("{\"message\": \"Thiếu tham số id!\"}");
            return;
        }

        String requestData = req.getReader().lines().collect(Collectors.joining());
        Laborer laborer = dataConverter.parse(requestData);
        if (laborer == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setContentType(resolveContentType(req));
            res.getWriter().write("{\"message\": \"Dữ liệu không hợp lệ!\"}");
            return;
        }

        laborer.setId(Integer.parseInt(idParam));

        // Reuse AddLaborerCommand because Hibernate save() uses merge => update if id exists
        Command updateCmd = new AddLaborerCommand(laborer);
        laborerService.executeCommand(updateCmd);

        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType(resolveContentType(req));
        res.getWriter().write("{\"message\": \"Cập nhật thành công!\"}");
    }

    // Xóa (DELETE) - /api/laborers?id=1
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // DELETE doesn't need body parsing, but keep content-type consistent
        String idParam = req.getParameter("id");
        if (idParam != null) {
            Command deleteCmd = new DeleteLaborerCommand(Integer.parseInt(idParam));
            laborerService.executeCommand(deleteCmd);

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType(resolveContentType(req));
            res.getWriter().write("{\"message\": \"Xóa thành công!\"}");
        }
    }

    private DataConverter resolveConverter(HttpServletRequest req) {
        // Priority:
        // 1) query param: ?converter=gson|jackson|xml
        // 2) request Content-Type header (xml vs json)
        // 3) default: gson
        String converter = req.getParameter("converter");
        if (converter == null || converter.isBlank()) {
            String ct = req.getContentType();
            if (ct != null) {
                String lct = ct.toLowerCase();
                if (lct.contains("xml")) converter = "xml";
                else if (lct.contains("json")) converter = "json";
            }
        }
        ConverterType type = ConverterType.from(converter);
        return ConverterFactory.from(type);
    }

    private String resolveContentType(HttpServletRequest req) {
        String override = req.getParameter("converter");
        ConverterType type = ConverterType.from(override);
        return type.isXml() ? "application/xml; charset=UTF-8" : "application/json; charset=UTF-8";
    }
}