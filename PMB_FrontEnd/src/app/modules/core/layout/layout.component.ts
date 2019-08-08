import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  providers: [LocalStorageService]
})
export class LayoutComponent implements OnInit, OnDestroy {
  user: any;
  showSidebar: boolean;
  sidebarWidth: string = "0";
  mainWidth: string = "100%";
  sidebarSizeSubscription: Subscription;

  constructor(private localStorageService: LocalStorageService,
    private route: ActivatedRoute,
    private statusService: StatusService,
    private router: Router) { }

  ngOnInit() {
    this.user = this.localStorageService.fetchUserName();
    if (this.user == null) {
      this.router.navigate([], { relativeTo: this.route });
    }
    this.sidebarSizeSubscription = this.statusService.sidebarSizeSubject.
      subscribe((data: any) => {
        this.sidebarWidth = data["sidebarWidth"];
        this.mainWidth = data["mainWidth"];
      });
  }

  ngOnDestroy() {
    this.sidebarSizeSubscription.unsubscribe();
  }
}
