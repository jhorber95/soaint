export interface IProducto {
  id?: number;
  nombre?: string;
  precion?: number;
}

export class Producto implements IProducto {
  constructor(public id?: number, public nombre?: string, public precion?: number) {}
}
