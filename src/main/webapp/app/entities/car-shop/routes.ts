import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CarShopComponent } from 'app/entities/car-shop/car-shop.component';
import { CarDetailComponent } from 'app/entities/car-shop/car-detail.component';
import { Injectable } from '@angular/core';
import { DetalleVenta, IDetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from 'app/entities/detalle-venta/detalle-venta.service';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

Injectable({ providedIn: 'root' });
export class VentaResolve implements Resolve<IDetalleVenta[]> {
  constructor(private service: DetalleVentaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalleVenta[]> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.getByVenta(id).pipe(
        flatMap((detalleVenta: HttpResponse<DetalleVenta[]>) => {
          if (detalleVenta.body) {
            return of(detalleVenta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of([]);
  }
}

export const routes: Routes = [
  {
    path: '',
    component: CarShopComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'soaintApp.producto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'detail/:id',
    component: CarDetailComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'soaintApp.home.shop.detail',
    },
    resolve: {
      detail: VentaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];
