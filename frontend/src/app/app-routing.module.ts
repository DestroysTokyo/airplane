import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DownloadsComponent} from './component/downloads/downloads.component';
import {HomeComponent} from './component/home/home.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/home'
  },
  {
    component: DownloadsComponent,
    path: 'downloads'
  },
  {
    component: HomeComponent,
    data: {
      title: 'Home'
    },
    path: 'home'
  }
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forRoot(routes)]
})
export class AppRoutingModule {
}
