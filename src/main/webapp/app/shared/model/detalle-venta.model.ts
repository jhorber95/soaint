export interface IDetalleVenta {
  id?: number;
  ventaId?: number;
  pruductoNombre?: string;
  pruductoId?: number;
  pruductoPrecio?: number;
}

export class DetalleVenta implements IDetalleVenta {
  constructor(
    public id?: number,
    public ventaId?: number,
    public pruductoNombre?: string,
    public pruductoId?: number,
    public pruductoPrecio?: number
  ) {}
}
