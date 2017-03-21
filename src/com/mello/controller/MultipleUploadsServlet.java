package com.mello.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * 多文件上传 利用MultipartConfig注解【必须】以及Part类来处理 简单方便
 * Part会返回类型为文件的<input>的元素值
 * 如下：
 * content-type:contentType  【text/plain】
 * content-disposition:from-data; name="fieldName"【输入域的name值】; filename="filename"【上传的文件名】  【;相隔间还有空格】
 */
@MultipartConfig
public class MultipleUploadsServlet extends HttpServlet {

    private String getFileName(Part part) {
        String contentDispositionHeader = part.getHeader("content-disposition");
        String[] elements = contentDispositionHeader.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) { //忘记使用trim来去除前导空格 导致得不到文件名此处返回了null
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        Collection<Part> parts = req.getParts();
        try (PrintWriter pw = resp.getWriter()) {
            for (Part part : parts) {
                //如果用户选了文件并点击上传
                String contentType = part.getContentType();
                System.out.println(contentType);
                if (contentType != null) {
                    String fileName = getFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
                        String path = req.getServletContext().getRealPath("/WEB-INF") + "/" + fileName;
                        String path1 = req.getServletContext().getResource("/WEB-INF") + "/" + fileName;
                        part.write(path);
                        System.out.println(path + "\n" + path1);
                        pw.println("<p>fileName:</p>" + fileName);
                        pw.println("<p>" + part.getName() + "</p>");
                        pw.println("<p>" + part.getContentType() + "</p>");
                    }
                } else { //否则得到的上传类型将为空
                    String name = part.getName();
                    String fileValue = req.getParameter(name);
                    pw.println("<p>" + name + ":" + fileValue + "</p>");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
