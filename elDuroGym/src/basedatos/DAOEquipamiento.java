/*
 * The MIT License
 *
 * Copyright 2024 alumnogreibd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package basedatos;

import java.sql.Connection;
import aplicacion.Aula;
import aplicacion.Equipamiento;

import java.time.LocalDate;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alumnogreibd
 */
public class DAOEquipamiento extends AbstractDAO {

    public DAOEquipamiento(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public List<Equipamiento> obtenerEquipamientosPorAula(String nombreAula, String descripcionEquipamiento) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Equipamiento> equipamientos = new ArrayList<>();

        try {
            con = this.getConexion();

            // Consulta SQL para obtener equipamientos por aula y descripción
            String consulta = "SELECT DISTINCT e.id_equipamiento AS equipamiento_id, e.nombre AS equipamiento_nombre, " +
                    "e.fecha_mantenimiento, e.descripcion AS equipamiento_descripcion, " +
                    "a.id_aula AS aula_id, a.nombre AS aula_nombre, a.aforo " +
                    "FROM Equipamiento e ";

            // Si se proporciona un nombre de aula, se une con la tabla Aula
            if (nombreAula != null && !nombreAula.isEmpty()) {
                consulta += "INNER JOIN Aula a ON e.id_aula = a.id_aula WHERE a.nombre = ?";
            } else {
                consulta += "LEFT JOIN Aula a ON e.id_aula = a.id_aula"; // LEFT JOIN para obtener todos los equipamientos sin filtrar por aula
            }

            // Si se proporciona una descripción, se agrega la condición a la consulta
            if (descripcionEquipamiento != null && !descripcionEquipamiento.isEmpty()) {
                consulta += " WHERE e.descripcion LIKE ?";
            }

            pstmt = con.prepareStatement(consulta);

            // Configurar los parámetros en el PreparedStatement
            int index = 1;
            if (nombreAula != null && !nombreAula.isEmpty()) {
                pstmt.setString(index++, nombreAula);
            }

            if (descripcionEquipamiento != null && !descripcionEquipamiento.isEmpty()) {
                pstmt.setString(index++, "%" + descripcionEquipamiento + "%");
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Obtener los datos del equipamiento y del aula
                int idEquipamiento = rs.getInt("equipamiento_id");
                String nombreEquipamiento = rs.getString("equipamiento_nombre");
                LocalDate fechaMantenimiento = rs.getDate("fecha_mantenimiento").toLocalDate();
                String descripcion = rs.getString("equipamiento_descripcion");

                // Crear el objeto Aula
                int idAula = rs.getInt("aula_id");
                String nombreAulaResultado = rs.getString("aula_nombre");
                int aforo = rs.getInt("aforo");
                Aula aula = new Aula(idAula, nombreAulaResultado, aforo);

                // Crear el objeto Equipamiento y añadirlo a la lista
                Equipamiento equipamiento = new Equipamiento(aula, idEquipamiento, nombreEquipamiento, fechaMantenimiento, descripcion);
                equipamientos.add(equipamiento);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return equipamientos;
    }
}
