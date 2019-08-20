import { NgModule } from '@angular/core';
import { TabViewModule, RadioButtonModule, InputSwitchModule, DropdownModule, MultiSelectModule, InputTextareaModule, DataTableModule, CheckboxModule, DialogModule, CalendarModule, MessagesModule, MessageModule, ProgressSpinnerModule, ConfirmDialogModule, PanelModule } from 'primeng/primeng';
import { DateRangePickerModule } from '@syncfusion/ej2-angular-calendars';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';

@NgModule({
    imports: [
        TabViewModule,
        RadioButtonModule,
        InputSwitchModule,
        DropdownModule,
        PanelModule,
        MultiSelectModule,
        DateRangePickerModule,
        InputTextareaModule,
        DataTableModule,
        CheckboxModule,
        DialogModule,
        TableModule,
        ToastModule,
        CalendarModule,
        MessagesModule,
        MessageModule,
        ProgressSpinnerModule,
        ConfirmDialogModule,
           
    ],
    exports: [
        TabViewModule,
        RadioButtonModule,
        InputSwitchModule,
        PanelModule,
        DropdownModule,
        MultiSelectModule,
        DateRangePickerModule,
        InputTextareaModule,
        DataTableModule,
        CheckboxModule,
        DialogModule,
        TableModule,
        ToastModule,
        CalendarModule,
        MessagesModule,
        MessageModule,
        ProgressSpinnerModule,
        ConfirmDialogModule,    
    ],
    declarations: [],
    providers: []
})

export class PrimeNgModule {

}