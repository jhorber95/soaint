import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { SoaintTestModule } from '../../../test.module';
import { DetalleVentaComponent } from 'app/entities/detalle-venta/detalle-venta.component';
import { DetalleVentaService } from 'app/entities/detalle-venta/detalle-venta.service';
import { DetalleVenta } from 'app/shared/model/detalle-venta.model';

describe('Component Tests', () => {
  describe('DetalleVenta Management Component', () => {
    let comp: DetalleVentaComponent;
    let fixture: ComponentFixture<DetalleVentaComponent>;
    let service: DetalleVentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoaintTestModule],
        declarations: [DetalleVentaComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(DetalleVentaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalleVentaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalleVentaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DetalleVenta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.detalleVentas && comp.detalleVentas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DetalleVenta(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.detalleVentas && comp.detalleVentas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
