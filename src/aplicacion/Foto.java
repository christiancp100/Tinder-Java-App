
package aplicacion;

import javax.swing.ImageIcon;

/**
 *
 * @author Palmiro
 */
public class Foto {
    private String descripcion;
    private ImageIcon img;

    public Foto(String descripcion, ImageIcon img) {
        this.descripcion = descripcion;
        this.img = img;
    }
    
    public Foto(String descripcion, byte[] bImg) {
        this.descripcion = descripcion;
        this.img = new ImageIcon(bImg);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
    
    
}
