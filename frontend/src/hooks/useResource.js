import { useEffect, useState } from "react";

export function useResource(loader, dependencies) {
  const [state, setState] = useState({ data: null, loading: true, error: "" });

  useEffect(() => {
    let active = true;
    setState((current) => ({ ...current, loading: true, error: "" }));
    loader()
      .then((data) => {
        if (active) setState({ data, loading: false, error: "" });
      })
      .catch((error) => {
        if (active) setState({ data: null, loading: false, error: error.message });
      });
    return () => {
      active = false;
    };
  }, dependencies);

  return state;
}