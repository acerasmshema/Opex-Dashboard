import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit, OnDestroy {
  showSidebar: boolean;
  sidebarClass: string = "left1";
  mainClass: string = "main1";
  sidebarSizeSubscription: Subscription;

  constructor(private localStorageService: LocalStorageService,
    private statusService: StatusService,
    private router: Router) {
  }

  ngOnInit() {
    let user = this.localStorageService.fetchUserDetail();
    if (user == undefined || user == null) {
      this.router.navigateByUrl('/login');
    } 
    else {
      this.statusService.common.userDetail = user;
      this.statusService.common.selectedMill = this.statusService.common.userDetail.millRoles[0].selectedMill;
    }

    this.sidebarSizeSubscription = this.statusService.sidebarSizeSubject.
      subscribe((className: string) => {
        if (className === 'hide') {
          this.mainClass = "main1";
          this.sidebarClass = "left1";
        }
        else if (className === 'collapse') {
          this.mainClass = "main2";
          this.sidebarClass = "left2";
        }
        else if (className === 'show') {
          this.mainClass = "main3";
          this.sidebarClass = "left3";
        }
      });
  }

  ngOnDestroy() {
    this.sidebarSizeSubscription.unsubscribe();
  }
}
