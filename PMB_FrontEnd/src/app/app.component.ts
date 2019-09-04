import { Component, OnInit, OnDestroy } from '@angular/core';
import { TranslateService } from './shared/service/translate/translate.service';
import { Subscription } from 'rxjs';
import { StatusService } from './shared/service/status.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  isSpin: boolean = false;
  spinnerSubscription: Subscription;

  constructor(private translate: TranslateService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.spinnerSubscription = this.statusService.spinnerSubject.
      subscribe((isSpin: boolean) => {
        this.isSpin = isSpin;
        this.statusService.isSpin = isSpin;
      });
  }

  setLang(lang: string) {
    this.translate.use(lang);
  }

  ngOnDestroy() {
    this.spinnerSubscription.unsubscribe();
  }
}
