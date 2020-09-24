import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVenta, Venta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html',
})
export class VentaUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [],
    clienteId: [null, Validators.required],
  });

  constructor(
    protected ventaService: VentaService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venta }) => {
      if (!venta.id) {
        const today = moment().startOf('day');
        venta.fecha = today;
      }

      this.updateForm(venta);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(venta: IVenta): void {
    this.editForm.patchValue({
      id: venta.id,
      fecha: venta.fecha ? venta.fecha.format(DATE_TIME_FORMAT) : null,
      clienteId: venta.clienteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  private createFromForm(): IVenta {
    return {
      ...new Venta(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      clienteId: this.editForm.get(['clienteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICliente): any {
    return item.id;
  }
}
