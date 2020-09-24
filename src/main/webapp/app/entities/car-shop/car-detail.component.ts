import { Component, OnInit } from '@angular/core';
import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-car-detail',
  templateUrl: './car-detail.component.html',
  styles: [],
})
export class CarDetailComponent implements OnInit {
  carDetail: IDetalleVenta[];
  total = 0;

  constructor(protected activatedRoute: ActivatedRoute) {
    const strCar = localStorage.getItem('car');
    this.carDetail = strCar !== null ? JSON.parse(strCar) : [];

    this.carDetail?.forEach(i => {
      this.total += i.pruductoPrecio!;
      // eslint-disable-next-line no-console
      console.log(' --- price', i.pruductoPrecio);
    });
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detail }) => {
      this.carDetail = detail;
    });
  }
}
