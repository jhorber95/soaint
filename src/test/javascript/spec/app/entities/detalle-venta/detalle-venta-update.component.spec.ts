import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SoaintTestModule } from '../../../test.module';
import { DetalleVentaUpdateComponent } from 'app/entities/detalle-venta/detalle-venta-update.component';
import { DetalleVentaService } from 'app/entities/detalle-venta/detalle-venta.service';
import { DetalleVenta } from 'app/shared/model/detalle-venta.model';

describe('Component Tests', () => {
  describe('DetalleVenta Management Update Component', () => {
    let comp: DetalleVentaUpdateComponent;
    let fixture: ComponentFixture<DetalleVentaUpdateComponent>;
    let service: DetalleVentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoaintTestModule],
        declarations: [DetalleVentaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DetalleVentaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalleVentaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalleVentaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalleVenta(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalleVenta();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
