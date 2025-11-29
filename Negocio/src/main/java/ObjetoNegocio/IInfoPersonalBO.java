/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ObjetoNegocio;

import DTOS.InfoPersonalDTO;
import DTOS.UsuarioDTO;

/**
 *
 * @author Josel
 */
public interface IInfoPersonalBO {

    String obtieneNombre(UsuarioDTO usuario);

    double obtieneIngreso(UsuarioDTO usuario);

    UsuarioDTO obtieneUsuario(Long idUsuario);

    int obtieneEdad(UsuarioDTO usuario);
}
