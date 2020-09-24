import { Component, OnInit } from '@angular/core';
import { IProducto } from 'app/shared/model/producto.model';
import { combineLatest, Subscription } from 'rxjs';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { DetalleVenta, IDetalleVenta } from 'app/shared/model/detalle-venta.model';

@Component({
  selector: 'jhi-car-shop',
  templateUrl: './car-shop.component.html',
  styles: [],
})
export class CarShopComponent implements OnInit {
  products?: IProducto[];

  carDetail: IDetalleVenta[] = [];

  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = 8;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(protected productoService: ProductoService, protected activatedRoute: ActivatedRoute, protected router: Router) {
    const strCar = localStorage.getItem('car');
    this.carDetail = strCar !== null ? JSON.parse(strCar) : [];
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.productoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  protected onSuccess(data: IProducto[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/car'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.products = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  addToCar(item: IProducto): void {
    const detail: IDetalleVenta = {
      ...new DetalleVenta(),
      pruductoId: item.id,
      pruductoNombre: item.nombre,
      pruductoPrecio: item.precion,
    };

    this.carDetail?.push(detail);

    localStorage.setItem('car', JSON.stringify(this.carDetail));
  }
}
