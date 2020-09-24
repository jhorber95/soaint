import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  productos?: IProducto[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    protected router: Router,
    protected productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.loadPage(1);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 1;

    this.productoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  login(): void {
    this.loginModalService.open();
  }

  sort(): string[] {
    return ['id' + ',' + (this.ascending ? 'asc' : 'desc')];
  }

  protected onSuccess(data: IProducto[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.productos = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
