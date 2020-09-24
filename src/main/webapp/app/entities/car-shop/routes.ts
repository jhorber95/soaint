import { Routes } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CarShopComponent } from 'app/entities/car-shop/car-shop.component';
import { CarDetailComponent } from 'app/entities/car-shop/car-detail.component';

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
    path: 'detail',
    component: CarDetailComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'soaintApp.home.shop.detail',
    },
    canActivate: [UserRouteAccessService],
  },
];
