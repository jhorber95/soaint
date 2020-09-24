import { Moment } from 'moment';

export interface IVenta {
  id?: number;
  fecha?: Moment;
  clienteNombre?: string;
  clienteId?: number;
}

export class Venta implements IVenta {
  constructor(public id?: number, public fecha?: Moment, public clienteNombre?: string, public clienteId?: number) {}
}
