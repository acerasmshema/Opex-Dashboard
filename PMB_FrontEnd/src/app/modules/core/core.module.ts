import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { TranslatePipe } from './pipes/translate.pipe';
import { OrderByPipe } from './pipes/order-by.pipe';
import { SidebarComponent } from './sidebar/sidebar.component';
import { RouterModule } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { PrimeNgModule } from '../shared/primeng-modules';
import { DialogComponent } from './dialog/dialog.component';

@NgModule({
  declarations: [
    LayoutComponent,
    HeaderComponent,
    SidebarComponent,
    FooterComponent,
    DialogComponent,
    TranslatePipe,
    OrderByPipe
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    PrimeNgModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [
    LayoutComponent,
    FooterComponent,
    DialogComponent,
    TranslatePipe,
    OrderByPipe
  ],
})
export class CoreModule { }
