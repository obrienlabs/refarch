#!/bin/bash
#############################################################################
# Copyright © 2020 obrienlabs
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#        http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#############################################################################
#
# This provisioning script configures a vanilla kubernetes cluster for use
# documentation at
# http://wiki.obrienlabs.cloud/display/DEV/Kubernetes+Developer+Guide
# source from

usage() {
cat <<EOF
Usage: $0 [PARAMs]
example
sudo ./rke_setup.sh -b dublin -s rke.onap.cloud -e onap -l amdocs -v true
-u                  : Display usage
-b [branch]         : branch = main (required)
-l [username]       : login username account (use ubuntu for example)
EOF
}

install_charts() {
  #constants
  PORT=8880

#  echo "user: $USERNAME"



  echo "Installing for ${BRANCH}: "

#  echo "Verify all pods up on the kubernetes system - will return localhost:8080 until a host is added"
#  echo "kubectl get pods --all-namespaces"
#  kubectl get pods --all-namespaces
#  echo "install tiller/helm"
#  kubectl -n kube-system create serviceaccount tiller
#  kubectl create clusterrolebinding tiller --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
#  helm init --service-account tiller
#  kubectl -n kube-system  rollout status deploy/tiller-deploy

  echo "verify both versions are the same below"
  if [ "$USERNAME" == "root" ]; then
    helm version
  else
    sudo helm version
  fi

  kubectl version
  kubectl get services --all-namespaces
  kubectl get pods --all-namespaces
  echo "finished!"
}

BRANCH=
VALIDATE=false
USERNAME=ubuntu

while getopts ":b:u:v" PARAM; do
  case $PARAM in
    u)
      usage
      exit 1
      ;;
    b)
      BRANCH=${OPTARG}
      ;;
    v)
      VALIDATE=${OPTARG}
      ;;
    ?)
      usage
      exit
      ;;
    esac
done

if [[ -z $BRANCH ]]; then
  usage
  exit 1
fi

install_charts $BRANCH $VALIDATE

