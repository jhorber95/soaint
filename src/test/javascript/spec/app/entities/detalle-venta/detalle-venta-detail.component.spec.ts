import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoaintTestModule } from '../../../test.module';
import { DetalleVentaDetailComponent } from 'app/entities/detalle-venta/detalle-venta-detail.component';
import { DetalleVenta } from 'app/shared/model/detalle-venta.model';

describe('Component Tests', () => {
  describe('DetalleVenta Management Detail Component', () => {
    let comp: DetalleVentaDetailComponent;
    let fixture: ComponentFixture<DetalleVentaDetailComponent>;
    const route = ({ data: of({ detalleVenta: new DetalleVenta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoaintTestModule],
        declarations: [DetalleVentaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DetalleVentaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalleVentaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load detalleVenta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detalleVenta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
