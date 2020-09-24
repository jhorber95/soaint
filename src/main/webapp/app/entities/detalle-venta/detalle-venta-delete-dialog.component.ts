import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from './detalle-venta.service';

@Component({
  templateUrl: './detalle-venta-delete-dialog.component.html',
})
export class DetalleVentaDeleteDialogComponent {
  detalleVenta?: IDetalleVenta;

  constructor(
    protected detalleVentaService: DetalleVentaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalleVentaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('detalleVentaListModification');
      this.activeModal.close();
    });
  }
}
