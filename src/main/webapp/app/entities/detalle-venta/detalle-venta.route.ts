import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDetalleVenta, DetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from './detalle-venta.service';
import { DetalleVentaComponent } from './detalle-venta.component';
import { DetalleVentaDetailComponent } from './detalle-venta-detail.component';
import { DetalleVentaUpdateComponent } from './detalle-venta-update.component';

@Injectable({ providedIn: 'root' })
export class DetalleVentaResolve implements Resolve<IDetalleVenta> {
  constructor(private service: DetalleVentaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalleVenta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((detalleVenta: HttpResponse<DetalleVenta>) => {
          if (detalleVenta.body) {
            return of(detalleVenta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetalleVenta());
  }
}

export const detalleVentaRoute: Routes = [
  {
    path: '',
    component: DetalleVentaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'soaintApp.detalleVenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetalleVentaDetailComponent,
    resolve: {
      detalleVenta: DetalleVentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'soaintApp.detalleVenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetalleVentaUpdateComponent,
    resolve: {
      detalleVenta: DetalleVentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'soaintApp.detalleVenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetalleVentaUpdateComponent,
    resolve: {
      detalleVenta: DetalleVentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'soaintApp.detalleVenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
