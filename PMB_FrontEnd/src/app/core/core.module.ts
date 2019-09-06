import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { RouterModule } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { PrimeNgModule } from '../shared/primeng-modules';
import { DialogComponent } from './dialog/dialog.component';
import { TranslatePipe } from '../shared/pipes/translate.pipe';

@NgModule({
  declarations: [
    LayoutComponent,
    HeaderComponent,
    SidebarComponent,
    FooterComponent,
    DialogComponent,
    TranslatePipe
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    PrimeNgModule,
    ReactiveFormsModule,
  ],
  exports: [
    LayoutComponent,
    FooterComponent,
    DialogComponent,
    TranslatePipe,
  ],
})
export class CoreModule { }
