import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    apellido: [],
    dni: [],
    telefono: [],
    email: [],
    userId: [],
  });

  constructor(
    protected clienteService: ClienteService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      dni: cliente.dni,
      telefono: cliente.telefono,
      email: cliente.email,
      userId: cliente.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido: this.editForm.get(['apellido'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      email: this.editForm.get(['email'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
