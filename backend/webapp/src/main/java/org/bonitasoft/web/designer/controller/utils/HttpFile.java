/**
 * Copyright (C) 2015 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.web.designer.controller.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpFile {

    /**
     * Write headers and content in the response
     */
    public static void writeFileInResponse(HttpServletRequest request, HttpServletResponse response, Path filePath) throws IOException {

        if (filePath == null || Files.notExists(filePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setHeader("Content-Type", request.getServletContext().getMimeType(filePath.getFileName().toString()));
        response.setHeader("Content-Length", String.valueOf(filePath.toFile().length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filePath.getFileName() + "\"");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(filePath, out);
        }
    }
}