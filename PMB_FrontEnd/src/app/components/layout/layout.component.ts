import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from '../../services/localStorage/local-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  providers:[LocalStorageService]
})
export class LayoutComponent implements OnInit {
  user:any;
  constructor(private localStorageService:LocalStorageService,private router: Router) {

    this.user=this.localStorageService.fetchUserName();
    if(this.user==null){
      this.router.navigateByUrl('login');
    }
   }

  ngOnInit() {
    
  }

}
