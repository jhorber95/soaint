import { NgModule } from '@angular/core';
import { CarShopComponent } from './car-shop.component';
import { SoaintSharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { routes } from 'app/entities/car-shop/routes';
import { CarDetailComponent } from 'app/entities/car-shop/car-detail.component';

@NgModule({
  declarations: [CarShopComponent, CarDetailComponent],
  imports: [SoaintSharedModule, RouterModule.forChild(routes)],
})
export class CarShopModule {}
