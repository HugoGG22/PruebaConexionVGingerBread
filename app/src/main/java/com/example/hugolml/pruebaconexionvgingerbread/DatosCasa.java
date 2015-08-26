package com.example.hugolml.pruebaconexionvgingerbread;

/**
 * Created by Hugolml on 04/03/2015.
 */
public class DatosCasa
{
    private int IdCasa;
    private String calle;
    private String colonia;
    private String ciudad;
    private String tipo;
    private String para;
    private String monto;

    public DatosCasa(int IdCasa, String calle, String colonia, String ciudad, String para, String tipo, String monto) {
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public int getIdCasa() {
        return IdCasa;
    }

    public void setId(int id) {
        IdCasa = id;
    }

@Override
    public String toString()
    {
       return this.calle;
    }
}
