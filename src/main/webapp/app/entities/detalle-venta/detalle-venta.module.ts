import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoaintSharedModule } from 'app/shared/shared.module';
import { DetalleVentaComponent } from './detalle-venta.component';
import { DetalleVentaDetailComponent } from './detalle-venta-detail.component';
import { DetalleVentaUpdateComponent } from './detalle-venta-update.component';
import { DetalleVentaDeleteDialogComponent } from './detalle-venta-delete-dialog.component';
import { detalleVentaRoute } from './detalle-venta.route';

@NgModule({
  imports: [SoaintSharedModule, RouterModule.forChild(detalleVentaRoute)],
  declarations: [DetalleVentaComponent, DetalleVentaDetailComponent, DetalleVentaUpdateComponent, DetalleVentaDeleteDialogComponent],
  entryComponents: [DetalleVentaDeleteDialogComponent],
})
export class SoaintDetalleVentaModule {}
