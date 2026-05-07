package com.example.democuoikybackend.controller;

import com.example.democuoikybackend.adapter.DataConverter;
import com.example.democuoikybackend.adapter.GSonAdapter;
import com.example.democuoikybackend.command.*;
import com.example.democuoikybackend.model.Laborer;
import com.example.democuoikybackend.service.LaborerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/api/laborers/*")
public class LaborerController extends HttpServlet {
    private LaborerService laborerService;
    private DataConverter dataConverter;

    @Override
    public void init() {
        this.laborerService = new LaborerService();
        this.dataConverter = new GSonAdapter();
    }

    // Lấy danh sách (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // 1. Tạo Command
        GetAllLaboreCommand getCmd = new GetAllLaboreCommand();

        // 2. Yêu cầu Service thực thi
        laborerService.executeCommand(getCmd);

        // 3. Lấy kết quả từ Command ra
        String responseData = dataConverter.stringify(getCmd.getResultList());

        res.setContentType("application/json; charset=UTF-8");
        res.getWriter().write(responseData);
    }

    // Thêm/Cập nhật (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());

        // Dùng Adapter để parse (chuyển JSON string -> Object)
        Laborer laborer = dataConverter.parse(requestData);

        Command addCmd = new AddLaborerCommand(laborer);
        laborerService.executeCommand(addCmd);

        res.setStatus(HttpServletResponse.SC_CREATED);
        res.getWriter().write("{\"message\": \"Lưu thành công!\"}");
    }

    // Xóa (DELETE) - /api/laborers?id=1
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            // 1. Tạo Command xóa
            Command deleteCmd = new DeleteLaborerCommand(Integer.parseInt(idParam));

            // 2. Yêu cầu Service thực thi
            laborerService.executeCommand(deleteCmd);

            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("{\"message\": \"Xóa thành công!\"}");
        }
    }
}