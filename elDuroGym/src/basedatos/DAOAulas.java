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

import aplicacion.Aula;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alumnogreibd
 */
public class DAOAulas extends AbstractDAO{
    
    public DAOAulas(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    // Método para obtener aulas por su nombre
    public List<Aula> obtenerAulasPorNombre(String nombre) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Aula> aulas = new ArrayList<>();

        try {
            con = this.getConexion();

            // Consulta SQL para obtener aulas por su nombre
            String consulta = "SELECT id_aula, nombre, aforo FROM Aula";

            // Si nombre no es nulo ni vacío, agregamos la condición WHERE para filtrar por nombre
            if (nombre != null && !nombre.isEmpty()) {
                consulta += " WHERE nombre LIKE ?";
            }

            pstmt = con.prepareStatement(consulta);

            // Si nombre no es nulo ni vacío, configuramos el parámetro en el PreparedStatement
            if (nombre != null && !nombre.isEmpty()) {
                pstmt.setString(1, "%" + nombre + "%");
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Aula aula = new Aula(
                        rs.getInt("id_aula"),
                        rs.getString("nombre"),
                        rs.getInt("aforo")
                );
                aulas.add(aula);
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
        return aulas;
    }
}
    

