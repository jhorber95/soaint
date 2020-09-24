import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.SoaintClienteModule),
      },
      {
        path: 'venta',
        loadChildren: () => import('./venta/venta.module').then(m => m.SoaintVentaModule),
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.SoaintProductoModule),
      },
      {
        path: 'detalle-venta',
        loadChildren: () => import('./detalle-venta/detalle-venta.module').then(m => m.SoaintDetalleVentaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SoaintEntityModule {}
